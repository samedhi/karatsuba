(ns karatsuba.events
  (:require
   [re-frame.core :as re-frame]
   [karatsuba.config :as config]))

(re-frame/reg-event-db
 :initialize-db
 (fn [_ _]
   config/default-db))
