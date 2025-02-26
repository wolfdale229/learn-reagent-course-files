(ns giggin.components.checkout-modal
  (:require [reagent.core :as r]))

(defn checkout-modal []
  (let [modal (r/atom false)
        toggle-modal #(reset! modal %)]
    (fn []
      [:div.checkout-modal
       [:button.btn.btn--secondary.float--left.tooltip {:data-tooltip "Make payments"
                                                        :on-click #(toggle-modal true)}
        "Checkout"]
       [:div.modal (when @modal {:class "active"})
        [:div.modal__overlay]
        [:div.modal__container
         [:div.modal__body
          [:div.payment
           [:img.payment-logo {:src "img/paypal.svg" :alt "Paypal logo"}]
           [:img.payment-logo {:src "img/stripe.svg" :alt "Stripe logo"}]
           ]]
         [:div.modal__footer
          [:button.btn.btn--link.tooltip.float--left {:data-tooltip "Cancel Payments"
                                                      :on-click #(toggle-modal false)}
           "Cancel"]]]]])))
