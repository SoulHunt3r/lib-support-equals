# lib-support-equals

## interface EqualsPartial

### boolean equalsPartial(Object dest)

Indicates whether some other object is "partially equal to" this one, 
comparing all not-null fields with dest object.

## hamcrest.matcher CustomMatcherEqualsPartialReverseOrder.equalsPartial(T match)

for use with `errorCollector.checkThat(foo, CustomMatcherEqualsPartialReverseOrder.equalsPartial(bar));`