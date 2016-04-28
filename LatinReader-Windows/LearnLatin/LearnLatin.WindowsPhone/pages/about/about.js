/*
    Learn Latin for Windows Phone
    Version 1.1
    Eric Schmidt
    2014-11-25

*/
(function () {
    "use strict";

    WinJS.UI.Pages.define("/pages/about/about.html", {

        ready: function (element, options) {
            // Show version info in the about page.
            var pkId = Windows.ApplicationModel.Package.current.id;
            var versionString = "Current version: " +
                pkId.version.major + "." + pkId.version.minor + "." + pkId.version.build + "." + pkId.version.revision;
            element.querySelector(".version").textContent = versionString;
        }
    });
})();
