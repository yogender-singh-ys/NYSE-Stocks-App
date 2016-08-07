# Overview
This app displays NYSE stocks value in `RecyclerView` allows to add, delete and graphically show historic data. Support multiple languages and  shows stock data in Android Widget.
Pulls stocks once every hour after the app has been opened.

# Features
- `RecyclerView` use with `RecyclerView.ViewHolder` adapter
- Use of `GestureDetector` and `RecyclerView.OnItemTouchListener` on `RecyclerView`
- Create settings using `Preference` and saving and using with `SharedPreferences`
- MPAndroidChartLibrary for displaying charts
- support for `RTL` language
- Yahoo Stock API to fetch NYE stock data 
- Use of `ContentProvider`
- Android Widget with `ListView`, `RemoteViewsService` and `RemoteViewsService.RemoteViewsFactory`
- `GcmTaskService` for making periodic data fetch

# Issues
- Needs upgraded UI for `GraphActivity`
- When app is offline should show a message in place of `RecyclerView` updated from `Service`

# Suggestions
Any kind of code enhancement and improvements are highly appreciated. Please fork or create a brach. Thanks :-)
