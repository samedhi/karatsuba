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

;; +BIG +SMALL => +
;; +BIG -SMALL => +
;; -BIG +SMALL => -
;; -BIG -SMALL => -

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

(defn variadic-add [& xs]
  (reduce add xs))

(defn shift-left
  "Shift string s to the left (fill with `n` '0')"
  [s n]
  (string/join (cons s (repeat n "0"))))

#_(shift-left "123" 0)
#_(shift-left "123" 3)

(defn zero-padd
  "Add zero or more '0' to the left of 's so that 's is of length 'l"
  [s l]
  (let [c (count s)
        delta (- l c)]
    (str (string/join (repeat delta "0")) s)))

#_(zero-padd "123" 5)

(defn karatsuba
  "Take 2 base 10 integers (as strings) and returns their multiplication (as a string)"
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
          z1 (- (karatsuba (add l1 h1) (add l2 h2))
                z0
                z2)]
      (variadic-add (shift-left z2 (* 2 m))
                    (shift-left z1 (* 1 m))
                    (shift-left z0 (* 0 m))))))

#_(karatsuba "16" "13")

#_(karatsuba "1000" "130")

#_(= (karatsuba "12345" "6789")
     (str (* 12345 6789)))

#_(= (karatsuba "1234523423432324" "67899092324")
     (* 1234523423432324 67899092324))
