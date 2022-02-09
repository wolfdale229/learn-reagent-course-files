(ns giggin.components.orders
  (:require [giggin.state :as state :refer [gigs]]
            [giggin.helper :refer [format-price]]
            [giggin.components.checkout-modal :refer [checkout-modal]]))

(defn total []
  (->> @state/orders
       (map (fn [[id quant]] (* (get-in @gigs [id :price]) quant)))
       (reduce +)))

(defn orders []
  (let [remove-from-order #(swap! state/orders dissoc %)
        remove-all-orders #(reset! state/orders {})]
    [:aside
     (if (empty? @state/orders)
       [:div.empty
        [:div.title "You don't have any orders"]
        [:div.subtitle "Click on + to add an order"]]
       [:div.order
        [:div.body
         (for [[id quant] @state/orders]
           [:div.item {:key id}
            [:div.img
           [:img {:src (get-in @gigs [id :img])
                  :alt (get-in @gigs [id :title])}]]
          [:div.content
           [:p.title (str (get-in @gigs [id :title]) " \u00D7 " quant)]]
          [:div.action
           [:div.price (format-price (* quant (get-in @gigs [id :price]))) ]
           [:button.btn.btn--link.tooltip {:data-tooltip "Remove order"
                                           :on-click #(remove-from-order id)}
            [:i.icon.icon--cross]]]])]
      [:div.total
       [:hr]
       [:div.item
        [:div.content "Total"]
        [:div.action
         [:div.price (format-price (total))]]
        [:button.btn.btn--link.float--right.tooltip {:data-tooltip "Remove all"
                                             :on-click #(remove-all-orders)}
         [:i.icon.icon--delete]]]]
        [checkout-modal]])]))
