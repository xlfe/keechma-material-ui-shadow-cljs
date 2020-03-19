(ns forms.ui.main
  (:require [keechma.ui-component :as ui]
            [reagent.core :as reagent]
            ["@material-ui/core/styles" :refer [ThemeProvider createMuiTheme]]
            ["@material-ui/core/colors" :as mui-colors]
            ["@material-ui/core/CssBaseline" :default mui-CssBaseline]
            ["@material-ui/core/Avatar" :default mui-avatar]
            ["@material-ui/core/Button" :default mui-button]
            ["@material-ui/icons/Android" :default AndroidIcon]))

(defn custom-theme
  []
  (createMuiTheme (clj->js #js {:palette #js {:type "light"
                                              :primary (.-blue mui-colors)
                                              :secondary (.-orange mui-colors)}
                                :typography #js {:useNextVariants true}})))

(defn init [_]
        [:> ThemeProvider
                   {:theme (custom-theme)}
                   [:<>
                    [:> mui-CssBaseline]
                    [:h1 "This is my first, simple heading"]
                    [:> mui-avatar
                     [:> AndroidIcon {:color :primary}]]
                    _]])
                  

(defn inc-if-odd
  "Increments the counter if the current value is odd."
  [ctx counter-val]
  (when (odd? counter-val)
    (ui/send-command ctx :inc)))

(defn inc-async
  "Increments the counter after 1 second."
  [ctx]
  (.setTimeout js/window #(ui/send-command ctx :inc) 1000))

(defn render
  [ctx]
  (let [counter-sub (ui/subscription ctx :counter-value)]
    (fn []
     (init
      [:p
       (str "Did click " @counter-sub " times ")
       [:> mui-button {:on-click #(ui/send-command ctx :inc)} "+"]
       " "
       [:> mui-button {:on-click #(ui/send-command ctx :dec)} "-"]
       " "
       [:> mui-button {:on-click #(inc-if-odd ctx @counter-sub)} "Increment IF odd"]
       " "
       [:> mui-button {:on-click #(inc-async ctx)} "Increment async"]]))))

(def component
  (ui/constructor {:renderer render
                   :subscription-deps [:counter-value]}))
