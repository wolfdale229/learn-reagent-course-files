(ns giggin.components.gigs
  (:require [giggin.state :as state]
            [reagent.core :as r]
            [giggin.components.gig-editor :refer [gig-editor]]
            [giggin.helper :refer [format-price]]))

(def new-gig-id
    (keyword (str "gig-" (+ (count @state/gigs) 1))))

(defn gigs []
  (let [modal (r/atom {:active false})
        initial-value {:id nil :artist "" :title "" :desc "" :img "" :price 0 :sold-out false}
        values (r/atom initial-value)
        toggle-modal (fn [{:keys [active gig]}]
                       (swap! modal assoc :active active)
                       (reset! values gig))
        add-to-order #(swap! state/orders update % inc)
        upsert-gig (fn [{:keys [id title desc img price sold-out artist]}]
                     (swap! state/gigs assoc id {:id (or id new-gig-id)  :artist artist :title title
                                                 :price price :desc desc
                                                 :img img :sold-out sold-out})
                     (toggle-modal {:active false :gig initial-value}))]
    [:main
     [:div.gigs
      [:button.add-gig
       {:on-click #(toggle-modal {:active true :gig initial-value})}
       [:div.add__title
        [:i.icon.icon--plus]
        [:p "Add gig"]]]
      [gig-editor {:modal modal :values values
                   :toggle-modal toggle-modal :upsert-gig upsert-gig
                   :initial-value initial-value}]
      (for [{:keys [id title price desc img artist]:as gig} (vals @state/gigs)]
        [:div.gig  {:key id}
         [:img.gig__artwork.gig__edit {:src img
                                       :alt title
                                       :on-click #(toggle-modal {:active true
                                                                 :gig gig})}]
         [:div.gig__body
          [:div.gig__title
          [:div.btn.btn--primary.float--right.tooltip
           {:data-tooltip "Add to order"
            :on-click #(add-to-order id)}
           [:i.icon.icon--plus]] title]
         [:p.gig.price (format-price price)]
         [:p.gig__desc desc]
         ]])]]))
