FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# Note RL: because of the way files are generated by the main recipe, they will
# be overwritten before do_install.
SRC_URI += " \
  file://hacServer.cfg.override \
  file://sdkName.txt.override \
  file://sdkVersion.txt.override"

do_install_append() {
  install -d ${D}/etc/default/
  install -m 0644 ${WORKDIR}/sdkVersion.txt.override ${D}/etc/default/sdkVersion.txt
  install -m 0644 ${WORKDIR}/sdkName.txt.override ${D}/etc/default/sdkName.txt
  install -m 0644 ${WORKDIR}/hacServer.cfg.override ${D}/etc/default/hacServer.cfg
}

