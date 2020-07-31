# HighlightTextView
 A TextView with lightweight-highlighting.

## Demo
| Base | Color | Width | Radius |
|:---:|:---:|:---:|:---:|
|<img src='https://user-images.githubusercontent.com/32336889/88354423-d0819680-cd9b-11ea-851a-78c19b04bbc2.gif'/>|<img src='https://user-images.githubusercontent.com/32336889/88354429-d37c8700-cd9b-11ea-9ba5-c44e6755c852.gif'/>|<img src='https://user-images.githubusercontent.com/32336889/88354440-da0afe80-cd9b-11ea-8a67-43d5e8e03ad1.gif'/>|<img src='https://user-images.githubusercontent.com/32336889/88354435-d7100e00-cd9b-11ea-8384-1ca179070807.gif'/>|

## Gradle
Progressing...

## Usage
### Xml
``` xml
<com.minseok.hightlight.HighlightTextView
        android:id="@+id/text_sample_sentence"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="@dimen/default_margin"
        android:text="Lorem ipsum dolor sit amet,"
        android:textSize="25sp"
        app:highlightText="ipsum,dolor"
        app:highlightWidth="8dp"
        app:highlightRadius="4dp"/>
```

### Kotlin
``` kotlin
val highlightView = findViewById<HighlightTextView>(R.id.highlight_view)
val input = findViewById<EditText>(R.id.input)
input.addTextChangedListener {
    highlightView.highlight(it.toString())
}
```

## +
* Light, Fast highlighting than [Spannable](https://developer.android.com/reference/android/text/Spannable) case.

## -
* Working only for a single line
* **Not stable yet**


## Soon
* Support rounded corners of highlight
* Support a highlight of multiline
* Support a highlight of mulitword
