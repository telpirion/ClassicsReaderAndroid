/*
    Learn Latin for Windows Runtime
    Version 1.0
    Eric Schmidt
    2014-07-10
*/
(function () {
    "use strict";

    WinJS.Binding.optimizeBindingReferences = true;

    var app = WinJS.Application;
    var activation = Windows.ApplicationModel.Activation;
    var nav = WinJS.Navigation;
    app.dictionary = "";

    app.addEventListener("activated", function (args) {

        WinJS.Utilities.startLog();

        if (args.detail.kind === activation.ActivationKind.launch) {

            if (app.sessionState.history) {
                nav.history = app.sessionState.history;
            }

            // If this is running on the phone, wire up the event handlers
            // to the app bar buttons.
            if (WinJS.Utilities.isPhone) {
                $(".navbar").click(function (evt) {
                    var location = evt.target.dataset.location;
                    WinJS.Navigation.navigate(location);
                });
            }

            // Load the Latin dictionary from the session state object or the app package.
            if (app.sessionState.dictionary) {
                Dictionary.load(app.sessionState.dictionary);
            }
            else {
                Dictionary.load();
            }

            // Load the 'About' page into the Settings charm for 
            // Windows desktop version of the app.
            if (WinJS.UI.SettingsFlyout != undefined) {
                app.onsettings = function (e) {
                    e.detail.applicationcommands = {
                        "about": { title: "About", href: "/pages/about/about.html" },
                        "help" : { title: "Help", href: "/pages/help/help.html" }
                    };
                    WinJS.UI.SettingsFlyout.populateSettings(e);
                }
            }

            // Update all of the UI.
            args.setPromise(WinJS.UI.processAll().then(function () {
                if (nav.location) {
                    nav.history.current.initialPlaceholder = true;
                    return nav.navigate(nav.location, nav.state);
                } else {
                    return nav.navigate(Application.navigator.home);
                }
            }));
        }
    });

    // Save the user's current reading state.
    app.oncheckpoint = function (args) {

        app.sessionState.history = nav.history;
        app.sessionState.dictionary = Dictionary.getData();
        var p = Notes.serialize();
        args.setPromise(p);
    };

    app.onerror = function (err) {

        var message = err.detail.errorMessage ||
            (err.detail.exception && err.detail.exception.message) ||
            err.detail.error.message ||
            "Indeterminate error";

        var messageDialog =
            new Windows.UI.Popups.MessageDialog(
                message,
                "Something bad happened ...");

        messageDialog.showAsync();

        return true;
    }

    app.start();
})();
