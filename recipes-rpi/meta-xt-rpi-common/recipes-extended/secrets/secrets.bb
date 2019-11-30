SUMMARY = "Install all files from ${SMARTHOME_SECRETS_DIR} into \
${SMARTHOME_RPI_MNT_SECRET}. These can be ssh keys etc. \
If ${SMARTHOME_SECRETS_DIR} is not set or empty then generate \
ssh keys."

LICENSE = "CLOSED"

S = "${WORKDIR}"

FILES_${PN} += " \
    ${SMARTHOME_RPI_MNT_SECRET}/* \
"

generate_key() {
    local FILE="$1"
    local TYPE=$2
    local DIR="$(dirname "$FILE")"

    ssh-keygen -q -f "${FILE}.tmp" -N '' -t $TYPE

    # Atomically rename file public key
    mv -f "${FILE}.tmp.pub" "${FILE}.pub"

    # This sync does double duty: Ensuring that the data in the temporary
    # private key file is on disk before the rename, and ensuring that the
    # public key rename is completed before the private key rename, since we
    # switch on the existence of the private key to trigger key generation.
    # This does mean it is possible for the public key to exist, but be garbage
    # but this is OK because in that case the private key won't exist and the
    # keys will be regenerated.
    #
    # In the event that sync understands arguments that limit what it tries to
    # fsync(), we provided them. If it does not, it will simply call sync()
    # which is just as well
    sync "${FILE}.pub" "$DIR" "${FILE}.tmp"

    mv "${FILE}.tmp" "$FILE"

    # sync to ensure the atomic rename is committed
    sync "$DIR"
}

# Force updating external files
do_install[nostamp] = "1"

do_install() {
    install -d ${D}/${SMARTHOME_RPI_MNT_SECRET}
    if [ -z "${SMARTHOME_SECRETS_DIR}" ]; then
        local dst=${D}/${SMARTHOME_RPI_MNT_SECRET}/ssh

        install -d "$dst"
        echo "No secrets provided, generating ssh keys into $dst..."

        echo "Generating ssh RSA key..."
        generate_key "$dst/ssh_host_rsa_key" rsa

        echo "Generating ssh ECDSA key..."
        generate_key "$dst/ssh_host_ecdsa_key" ecdsa

        echo "Generating ssh DSA key..."
        generate_key "$dst/ssh_host_dsa_key" dsa

        echo "Generating ssh ED25519 key..."
        generate_key "$dst/ssh_host_ed25519_key" ed25519
    else
        echo "Using secrets from ${SMARTHOME_SECRETS_DIR}..."
        cp -rfL ${SMARTHOME_SECRETS_DIR}/* ${D}/${SMARTHOME_RPI_MNT_SECRET}/
    fi
}
