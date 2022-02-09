(ns giggin.fb.init)


(defn firebase-int []
  (.log js/console firebase)
  )

(defn fire-analysis [app]
  (analytics app))
