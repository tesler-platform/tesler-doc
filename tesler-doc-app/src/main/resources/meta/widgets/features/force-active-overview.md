# Force Active Fields

Sometimes it's useful to establish a dependency of some kind between two fields.  
Let's take a look at a [Dictionary field](#/screen/components/view/dictionary/). Dictionary field is described with an array of available values (`values` property), so we can have a dictionary of countries, for example. But what if we need a second dictionary for languages used in country, selected in the first dictionary? It seems that we need somehow to set our `values` property dynamically.

The `forceActive` field option handles such cases by enforcing a `preview save`, which is essentially a POST request to the [Row Meta Controller](https://github.com/tesler-platform/tesler/blob/master/tesler-core/src/main/java/io/tesler/core/controller/UniversalRowMetaController.java)) that will fire an appropriate business component `Field Meta Builder` and will respond with a new row meta. The logic described in `Field Meta Builder` can decide on setting different `values` property, or any other row meta functionality (e.g. make diffirent field mandatory, or disable it, or force some value, etc.)
