(ns giggin.components.gig-editor)

(defn form-class [{:keys [id type value values]}]
  [:div.form__group
   [:label.form__label {:for id} id]
   [:input.form__input {:type type
                        :id id 
                        :value value
                        :on-change #(swap! values assoc
                                           (keyword id) (-> % .-target .-value))}]])

(defn gig-editor
  [{:keys [modal values toggle-modal upsert-gig initial-value]}]
  (let [{:keys [title artist desc img price sold-out]} @values]
  [:div.modal (when (:active @modal) {:class "active"})
   [:div.modal__overlay]
   [:div.modal__container
    [:div.modal__body
     [form-class {:id "title" :type "text"
                  :value title :values values}]

     [form-class {:id "artist" :type "text"
                  :value artist :values values}]

     [form-class {:id "desc" :type "text"
                  :value desc :values values}]

     [form-class {:id "img" :type "text"
                  :value img :values values}]

     [form-class {:id "price" :type "number"
                  :value price :values values}]

     [:div.form__group
      [:label.form__label {:for "sold-out"} "Sold-out"]
      [:label.form__switch
       [:input {:type :checkbox
                :checked sold-out
                :on-change #(swap! values assoc
                                   :sold-out (-> % .-target .-checked))}]
       [:i.form__icon]]]]
    [:div.modal__footer
     [:button.btn.btn--link.float--left {:on-click #(toggle-modal {:active false :gig initial-value})}
      "cancel"]
     [:button.btn.btn--link.float--right.tooltip {:data-tooltip "Save gig"
                                                  :on-click #(upsert-gig @values)}
      "Save"]
     ]]]))
