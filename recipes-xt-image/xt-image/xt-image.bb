SMARTHOME_BUILD_TARGETS ?= "vaillant homeassist"

python __anonymous () {
    targets = d.getVar('SMARTHOME_BUILD_TARGETS', True).split()
    if "vaillant" in targets:
        d.appendVarFlag("do_build", "depends", " vaillant-image:do_${BB_DEFAULT_TASK} ")
    if "homeassist" in targets:
        d.appendVarFlag("do_build", "depends", " homeassistant-image:do_${BB_DEFAULT_TASK} ")
}
