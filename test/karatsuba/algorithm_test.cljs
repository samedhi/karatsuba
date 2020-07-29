(ns karatsuba.algorithm-test
  (:require [karatsuba.algorithm :as sut]
            [cljs.test :as t :include-macros true]))

(t/deftest conversion-test
  (t/is (= 1 (int "2"))))
