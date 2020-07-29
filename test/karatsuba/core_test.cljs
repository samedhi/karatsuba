(ns karatsuba.core-test
  (:require
   [karatsuba.core :as sut]
   [karatsuba.algorithm-test]
   [cljs.test :as t :include-macros true]))

(enable-console-print!)

(t/run-tests
 'karatsuba.algorithm-test)
