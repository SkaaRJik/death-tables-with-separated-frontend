import Fingerprint2 from 'fingerprintjs2'




var options = {}
export default {

    makeFinger: () => {

        Fingerprint2.get(options, function (components) {
            // components is array of {key: 'foo', value: 'component value'}
        console.log(components)
        })  }
}

