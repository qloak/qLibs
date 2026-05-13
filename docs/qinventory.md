# qInventory

Item builders and stack utilities.

## ItemBuilder

```java
Item.Properties props = ItemBuilder.create()
    .stackSize(16)
    .durability(256)
    .fireResistant()
    .food(6, 0.8f)
    .build();
```

Returns a normal `Item.Properties` you pass to `new Item(props)`.

## StackUtils

```java
// compare ignoring NBT
boolean same = StackUtils.matches(stackA, stackB);

// copy with count
ItemStack copy = StackUtils.copyWithCount(stack, 32);

// safe shrink
StackUtils.shrink(player, stack, 1);
```

Handles creative mode players correctly (don't actually remove items).
