(ns prod
  (:require [forms.core.main :refer [restart-server]]
            [shadow.cljs.devtools.api :as shadow]))


(defn -main [& args]
  (shadow/release :client)
  ;(restart-server)
  (println "Ready to go!"))
