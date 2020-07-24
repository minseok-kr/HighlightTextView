# HighlightTextView
 A TextView with lightweight-highlighting.

## Demo
| Base | Color | Width |
|:---:|:---:|:---:|
|<img src='https://user-images.githubusercontent.com/32336889/88352043-56e5aa80-cd93-11ea-8808-dccf70a184cd.gif' width='70%'/>|<img src='https://user-images.githubusercontent.com/32336889/88351878-cad38300-cd92-11ea-85cd-2669e3bf2b7f.gif' width='70%'/>|<img src='https://user-images.githubusercontent.com/32336889/88351890-d161fa80-cd92-11ea-8873-2b6f1246f10a.gif' width='70%'/>|

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
        app:highlightText="ipsum"
        app:highlightWidth="10dp" />
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
