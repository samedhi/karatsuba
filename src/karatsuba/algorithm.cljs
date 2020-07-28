(ns karatsuba.algorithm
  (:require
   [clojure.string :as string]))

;;procedure karatsuba(num1, num2)
;; if (num1 < 10) or (num2 < 10)
;; return num1 × num2

;; /* Calculates the size of the numbers. */
;; m = min(size_base10(num1), size_base10(num2))
;; m2 = floor(m / 2) 
;; /*m2 = ceil(m / 2) will also work */

;; /* Split the digit sequences in the middle. */
;; high1, low1 = split_at(num1, m2)
;; high2, low2 = split_at(num2, m2)

;; /* 3 calls made to numbers approximately half the size. */
;; z0 = karatsuba(low1, low2)
;; z1 = karatsuba((low1 + high1), (low2 + high2))
;; z2 = karatsuba(high1, high2)

;; return (z2 × 10 ^ (m2 × 2)) + ((z1 - z2 - z0) × 10 ^ m2) + z0

(defn split-lower-n-digits [s n]
  (->> s
       string/reverse
       (split-at n)
       (map reverse)
       reverse
       (map string/join)))

#_(split-lower-n-digits "12345" 3)
#_(split-lower-n-digits "6789" 3)
#_(split-lower-n-digits "130" 2)

(defn pow [n m]
  (js/Math.pow n m))

#_(pow 7 3)

(defn add
  "Take 2 base 10 integers (as strings) and returns their multiplication (as a string)"
  [s1 s2]
  (loop [[s1-digit & s1-rest] (reverse s1)
         [s2-digit & s2-rest] (reverse s2)
         carry 0
         acc []]
    (if (and (zero? carry) (nil? s1-digit) (nil? s2-digit))
      (string/join (reverse acc))
      (let [v (+ (int (or s1-digit 0)) (int (or s2-digit 0)) carry)]
        (recur
         s1-rest
         s2-rest
         (quot v 10)
         (conj acc (mod v 10)))))))

#_(add "7" "1")
#_(add "9" "1")
#_(add "9" "7")
#_(= (add "12312412" "198797234")
     (str (+ 12312412 198797234)))

(defn karatsuba
  "Take 2 base 10 integers (as strings) and returns their multiplication (as an integer)"
  [s1 s2]
  (if (or (-> s1 count (= 1)) (-> s2 count (= 1)))
    (* (int s1) (int s2))
    (let [m (js/Math.ceil
             (/ (min (count s1)
                     (count s2))
                2))
          [h1 l1] (split-lower-n-digits s1 m)
          [h2 l2] (split-lower-n-digits s2 m)
          z0 (karatsuba l1 l2)
          z2 (karatsuba h1 h2)
          z1 (- (karatsuba (str (+ (int l1) (int h1)))
                           (str (+ (int l2) (int h2))))
                z0
                z2)]
      (+ (* z2 (pow 10 (* 2 m)))
         (* z1 (pow 10 (* 1 m)))
         (* z0 (pow 10 (* 0 m)))))))

#_(karatsuba "16" "13" 0)

#_(karatsuba "1000" "130" 0)

#_(= (karatsuba "12345" "6789")
     (* 12345 6789))

#_(= (karatsuba "1234523423432324" "67899092324")
     (* 1234523423432324 67899092324))
