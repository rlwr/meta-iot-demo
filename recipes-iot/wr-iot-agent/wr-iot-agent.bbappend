FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://0001-increase-buffer.patch; \
"

SETTINGS_FILE = "${D}/var/wra/files/default/default_settings"

SERVER_DEFAULT = "ems.windriver.com"
SERVER_ACTUAL  = "wrpoc1.axeda.com"

MODEL_DEFAULT  = "Default_EMS-1_2-Model"
MODEL_ACTUAL   = "IOT_Demo_Gateway"

do_install_append() {
	sed -i s/${SERVER_DEFAULT}/${SERVER_ACTUAL}/g ${D}/var/wra/files/default/default_settings
	sed -i s/${MODEL_DEFAULT}/${MODEL_ACTUAL}/g ${D}/var/wra/files/default/default_settings
}
