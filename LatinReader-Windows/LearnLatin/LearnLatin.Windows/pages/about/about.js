// The about page in the SettingsFlyoout
(function () {
    "use strict";

    WinJS.UI.Pages.define("/pages/about/about.html", {

        ready: function (element, options) {

            // Show version info in the About page.
            var pkId = Windows.ApplicationModel.Package.current.id;
            var versionString = "Current version: " + 
                pkId.version.major + "." + pkId.version.minor + "." + pkId.version.build + "." + pkId.version.revision;
            element.querySelector(".version").textContent = versionString;

            // Add event handler to "back button" to dismiss the flyout.
            element.querySelector(".win-backbutton").addEventListener('click', function () {
                element.querySelector(".aboutSettingsFlyout").winControl.hide();
            });
        }
    });
})();
