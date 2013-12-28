(ns clj-wordpress.post
  [:use clj-wordpress.core]
  )

(defn new-post
  ""
  [config content tags deploy-or-not]
  (let [_ (:content {})
        _ (:tags {})]
    (with-wp config (wp 'newPost [content tags deploy-or-not]))))
