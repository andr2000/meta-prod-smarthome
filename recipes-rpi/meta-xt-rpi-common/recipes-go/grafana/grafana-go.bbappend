do_install_append() {
	rm -rf ${D}${libdir}/go/src/github.com/grafana/grafana/node_modules/node-sass/vendor/linux-x64-51/binding.node
}
