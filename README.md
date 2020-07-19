# HighlightTextView
 A TextView with light-highlighting.

## Demo
| Base | Color | 
|:---:|:---:|
|<img src='https://user-images.githubusercontent.com/32336889/87878304-62954200-ca1e-11ea-8f26-65ce1393b34a.gif' width='70%'/>|<img src='https://user-images.githubusercontent.com/32336889/87878366-b7d15380-ca1e-11ea-8140-86fe7d2da744.gif' width='70%'/>|

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
        app:highlightText="ipsum" />
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
