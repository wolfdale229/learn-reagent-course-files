(ns giggin.helper)

(defn format-price [amount]
  (str "\u20AC "(/ amount 100)))
