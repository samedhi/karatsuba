(ns karatsuba.views.root
  (:require
   [re-frame.core :as re-frame]
   [karatsuba.mui :as mui]
   [karatsuba.views.util :as views.util]
   [karatsuba.util :as util]))

(defn title []
  (let [greeting @(re-frame/subscribe [:greeting])]
    [mui/typography
     {:variant :h2
      :align :center}
     greeting]))

(defn component []
  [mui/container
   {:max-width "xl"}
   [title]
   [views.util/app-db-viewer]
   [views.util/footer]])
