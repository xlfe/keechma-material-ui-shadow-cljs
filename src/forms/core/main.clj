(ns forms.core.main
  (:require [aleph.http :as http]
            [taoensso.timbre :as timbre :refer [info]]))

;; Web server
;; ##################################
(defonce server (atom nil))

(defn restart-server []
  (let [port 8080]
    (when-let [current-server @server]
      (.close current-server)
      (info "Waiting to restart server…")
      (.wait-for-close current-server))
    (reset! server (http/start-server nil {:port port}))
    (info (str "HTTP server started on port " port))))


;; Entry point
;; ##################################
(defn -main [_]
  (restart-server)
  (info "Ready to go!"))
