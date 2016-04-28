(function () {
    "use strict";

    WinJS.UI.Pages.define("/pages/help/help.html", {

        ready: function (element, options) {
            
            // Show the help file list.
            $(".helpFilesList").addClass("visible");

            // Add event handler to back button.
            $("#backButton").click(function () {
                $(".helpSettingsFlyout")[0].winControl.hide();
            });

            // Add event handler to back link
            $(".return").click(function () {

                WinJS.Utilities.empty($(".helpFile")[0]);

                WinJS.UI.Animation.exitPage($(".helpFileView").addClass('no-show')[0]);
                WinJS.UI.Animation.enterPage($(".helpFilesList").removeClass('no-show')[0]);
            });

            // Add event handler to invoked event on ListView.
            $(".helpFilesList")[0].winControl.addEventListener("iteminvoked", function (evt) {
                var itemIndex = evt.detail.itemIndex;
                var item = Help.assets.getItem(itemIndex).data;
                $(".helpFile").html(toStaticHTML(item.info));

                WinJS.UI.Animation.exitPage($(".helpFilesList").addClass('no-show')[0]);
                WinJS.UI.Animation.enterPage($(".helpFileView").removeClass('no-show')[0]);

            });
        },

        unload: function () {
            
        }
    });
})();
