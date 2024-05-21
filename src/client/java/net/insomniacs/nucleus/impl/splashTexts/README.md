# Splash Text API

## TLDR

`namespace:texts/splashes.json`
```json5
{
    "default_style": {
        "color": "ff0000" // Sets every text's color to be red
    },
	"entries": [
		"Splash text!",
		{ "text": "Extra splash text!" }, // Advanced format
		{ "text": { "text": "JSON splash text!", "bold": true } }, // JSON text formatting
		{ "text": "Common splash text!", "weight": 10 }, // Weighted random chance
		{ "text": "Special splash text!", "date": "25/10" } // Date-specific splash texts
	]
}
```