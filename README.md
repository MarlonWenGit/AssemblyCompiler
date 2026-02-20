# Assembly Compiler (Kotlin Playground)

<img width="583" height="203" alt="image" src="https://github.com/user-attachments/assets/a33aa218-853e-42d2-bbec-c6f80f964e8e" />
<img width="583" height="203" alt="image" src="https://github.com/user-attachments/assets/a33aa218-853e-42d2-bbec-c6f80f964e8e" />


A very small assembly-style compiler + tester that runs entirely inside **Kotlin Playground**.

You paste this file into Kotlin Playground, paste your assembly program, press run, and it will automatically test your code using random 16-bit numbers.

Total setup time: about 5 minutes.

---

## 🚀 Quick Start

1. Open **Kotlin Playground** in your browser.
2. Copy the contents of `AssemblyCompiler.kt`.
3. Paste it into Kotlin Playground.
4. Scroll to the middle of the file and find:

```kotlin
val program = listOf(
