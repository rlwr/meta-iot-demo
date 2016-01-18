DESCRIPTION = "Node MQTT library"
HOMEPAGE = "https://github.com/mqttjs/MQTT.js.git"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://LICENSE.md;md5=a76320d876562ab514061e70044b74b7"

DEPENDS_append_class-target = "nodejs-native"

SRC_URI = "git://github.com/mqttjs/MQTT.js.git"
SRCREV = "9c3c3d5e83294677d0f4e4fe3d9a6257aa4556df"

S = "${WORKDIR}/git"

# v8 errors out if you have set CCACHE
CCACHE = ""

# we don't care about debug for the few binary node modules
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP_${PN} = "staticdev"

do_compile () {
    # changing the home directory to the working directory, the .npmrc will be created in this directory
    export HOME=${WORKDIR}

    # does not build dev packages
    npm config set dev false

    # access npm registry using http
    npm set strict-ssl false
    npm config set registry http://registry.npmjs.org/

    # configure http proxy if neccessary
    if [ -n "${http_proxy}" ]; then
        npm config set proxy ${http_proxy}
    fi
    if [ -n "${HTTP_PROXY}" ]; then
        npm config set proxy ${HTTP_PROXY}
    fi

    # configure cache to be in working directory
    npm set cache ${WORKDIR}/npm_cache

    # clear local cache prior to each compile
    npm cache clear

    # NPM is picky about arch names
#    if [ "${TARGET_ARCH}" == "i586" ]; then
#        npm config set target_arch ia32
#        export TARGET_ARCH=ia32
#    fi

    npm config set target_arch ia32
    export TARGET_ARCH=ia32

    npm install
    npm install --ignore-scripts --arch=${TARGET_ARCH}
}

# Just package everything
do_install () {

    install -d ${D}/opt/mqtt.js/
    cp -a ${S}/* ${D}/opt/mqtt.js/
}

FILES_${PN} = "/opt/mqtt.js"

