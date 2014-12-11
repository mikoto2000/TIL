require(["dojo/query", "dojo/fx", "dojo/fx/Toggler", "dojo/on"],
        function(query, fx, Toggler, on){
            var toggleTarget = query("#content")[0];
            var toggler = new dojo.fx.Toggler({
                node: toggleTarget,
                showFunc: dojo.fx.wipeIn,
                hideFunc: dojo.fx.wipeOut,
                isHide: false
            });

            var toggleTrigger = query("h1")[0];
            on(toggleTrigger, "click", function(){
                if (toggler.isHide) {
                    toggler.show();
                } else {
                    toggler.hide();
                }
                toggler.isHide = !toggler.isHide;
            });
        })
