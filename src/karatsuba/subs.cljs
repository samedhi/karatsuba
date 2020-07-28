(ns karatsuba.subs
  (:require
   [re-frame.core :as re-frame]
   [karatsuba.util :as util]))

(re-frame/reg-sub
 :greeting
 (fn [db]
   (:greeting db)))

(re-frame/reg-sub
 :pretty-print-db
 (fn [db]
   (util/pprint db)))
