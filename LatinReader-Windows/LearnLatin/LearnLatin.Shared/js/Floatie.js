(function () {
    "use strict";

    function Floatie(element, options) {
        this.element = element || document.createElement('div');
        this.element.draggable = true;
        this.element.winControl = this;
        this._disposed = false;
        this._parent = null;
        this.title = "";
        this.contentElement = document.createElement('div');
        this.adjustedX = 0;
        this.adjustedY = 0;

        WinJS.Utilities.addClass(this.element, "floating-menu");
        WinJS.Utilities.addClass(this.element, "win-disposable");
        this.element.ondblclick = function () {
            this.dispose();
        }.bind(this);

        var titleElement = document.createElement('h2');

        this.element.appendChild(titleElement);
        this.element.appendChild(this.contentElement);

        if (options) {
            this.element.style.top = options.top.toFixed() + "px";
            this.element.style.left = options.left.toFixed() + "px";
            this.title = options.title
            titleElement.textContent = this.title;
        }
        else {
            this.element.style.top = Number(window.innerHeight / 2).toFixed() + "px";
            this.element.style.left = Number(window.innerWidth / 2).toFixed() + "px";
        }

        this.element.draggable = true;

        var that = this;
        this.observer = new MutationObserver(function (mutations) {
            mutations.forEach(function (mutation) {
                if (mutation.addedNodes[0] == that.element) {
                    _initDragDrop(that);
                }
            });
        });

        this.onDragOver = function (evt) {
            var boundingRect = that.element.getBoundingClientRect();
            that.adjustedX = evt.x - boundingRect.left;
            that.adjustedY = evt.y - boundingRect.top;
        }

        this.onDrop = function (evt) {
            that.element.style.display = "";

            var rect = that.element.getBoundingClientRect();
            var divY = rect.top;
            var divX = rect.left;

            that.element.style.top = (evt.y - that.adjustedY) + "px";
            that.element.style.left = (evt.x - that.adjustedX) + "px";
        }

        this.observer.observe(document, { childList: true, subtree: true });
    }

    function _initDragDrop(control) {
        control._parent = control.element.parentElement;

        control.element.ondragstart = control.onDragOver;
        control._parent.ondragend = control.onDrop;

        control.observer.disconnect();
        control.observer = null;

    }

    WinJS.Namespace.define("CustomControls", {
        Floatie: WinJS.Class.define(Floatie,
            {
                dispose: function () {
                    this._disposed = true;

                    this._parent.removeEventListener("dragover", this.onDragOver);
                    this._parent.removeEventListener("drop", this.onDrop);

                    WinJS.Utilities.disposeSubTree(this.element);

                    this._parent.removeChild(this.element);
                    this._parent = null;
                }
            })
    });
})();