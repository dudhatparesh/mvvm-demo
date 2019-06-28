MyCakes - Assignment

Achieved objectives from the requirements.

1. When the app is started, load and display a list of cakes (Completed)
    a. Remove duplicate entries (Completed)
    b. Order entries by ​name (Completed)
    c. Display ​image​ and ​title​ for each entry (Completed)
    d. Display a divider between each entry (Achieved using CardView to create a distinction between rows. RecyclerView's item decorator does not have that good ui effect)
2. Display the cake ​description​ in some kind of popup when a cake entry is clicked (Completed)
3. Provide some kind of refresh option that reloads the list (Completed)
4. Display an error message if the list cannot be loaded (e.g. ​no network​) (Completed)

Extra credit
1. Handle orientation changes, ideally without reloading the list (Completed)
2. Provide an option to ​retry​ when an error is presented (Completed)
3. Animate in list items (e.g. ​fade in​ or f​all down​ animations) (Completed)

Improvements
1. With more time we could have used Dagger2 or Koin for dependency injection
2. We could have used databinding along with Navigation Components
