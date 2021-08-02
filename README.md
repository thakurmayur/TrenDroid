# TrenDroid
An Android project to show trending repositories of Android from GitHub using Android architecture components.

## Screens
![Trending1](https://user-images.githubusercontent.com/52519535/127805263-73a8995d-61c3-4cbb-bdb8-a4dc9ce36259.png)

![google_readme (1)](https://user-images.githubusercontent.com/52519535/127805484-8ebf105f-1462-4f23-9b8e-d5a3b57506d5.png)

## Functionality
The first screen shows trending repositories of Android from GitHub. After clicking on respective repository, app will show details view of that repository.
The app is using WorkManager to fetch repositories and its details in background after every 15 minutes.

App is design to work in offline mode as well.

## Libraries Used
1. CardView
2. RecyclerView
3. Retrofit 2
4. Picasso
5. MarkWon
6. JUnit
7. Room
8. WorkManger
9. LifeCycle
10. Dagger2
