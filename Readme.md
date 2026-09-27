# 🆘 SmartAidDistribution — GUI (Phase 2)

<p align="center">
  <img src="https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=openjdk" alt="Java"/>
  <img src="https://img.shields.io/badge/Interface-JavaFX-blue?style=for-the-badge" alt="JavaFX"/>
  <img src="https://img.shields.io/badge/Build-Maven-red?style=for-the-badge&logo=apachemaven" alt="Maven"/>
  <img src="https://img.shields.io/badge/Phase-2%20of%202-blueviolet?style=for-the-badge" alt="Phase 2"/>
</p>

<p align="center">
  A full JavaFX desktop application for managing humanitarian aid distribution —
  the final, graphical version of the SmartAidDistribution project.
</p>

> 📌 **This is Phase 2** — the polished GUI version. The original console-based
> version (Phase 1) is available in a separate repository: **[SmartAidDistribution](https://github.com/A7mcdd/SmartAidDistribution)**

---

## 📖 About the Project

SmartAidDistribution-GUI is a desktop application built with **JavaFX** for managing
aid distribution to registered beneficiaries — **families** and
**individuals** — across a set of supported cities. It builds on the core
logic of the Phase 1 console version, wrapped in a multi-screen graphical
interface with navigation, tables, forms, and dialogs.

---

## ✨ Features

- 🏠 **Dashboard** — overview screen with quick visuals and summary info
- 👥 **Beneficiaries View** — register and browse `Family` / `Individual` beneficiaries in a sortable table
- 📦 **Aid Items View** — manage aid item inventory (`FoodPackage`, `MedicalKit`, `EmergencyKit`, `WinterBag`)
- 🚚 **Distribution View** — record and browse distribution events by date
- 📊 **Reports View** — generate statistical summaries (most-served city, totals by category, date-range queries)
- 📁 **Files View** — save/load application data to text or binary files
- 🔔 **Alerts** — consistent info/error dialogs across the app (`Alertt` helper class)
- ⚠️ **Custom Exception Handling** — validation errors surfaced through dedicated exceptions

---

## 🧠 Object-Oriented & JavaFX Concepts Applied

| Concept | Where it's used |
|---|---|
| **Abstraction** | `AidItem` and `Beneficiary` are abstract base classes |
| **Inheritance** | Aid item and beneficiary subtypes extend their respective base classes |
| **Interfaces** | `FileOperations` defines the save/load contract implemented by `AidManager` |
| **Comparable** | `Family` and `Individual` support sorting |
| **Custom Exceptions** | `DuplicateRegistrationException` and related exceptions |
| **Serialization** | Binary persistence via `Serializable` |
| **JavaFX Scene Graph** | Each screen (`*View` classes) built as a reusable JavaFX `Region`/`VBox` component |
| **Event-driven UI** | Buttons and menus wired through JavaFX event handlers |

---

## 🗂️ Project Structure

```
demo1/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── application/
        │       ├── Main.java                # JavaFX entry point & navigation
        │       ├── AidManager.java          # Core logic: registration, records, reports, file I/O
        │       ├── AidSystemDriver.java     # Legacy console entry point (kept from Phase 1)
        │       ├── FileOperations.java      # Interface for save/load contracts
        │       ├── Alertt.java              # Reusable alert/dialog helper
        │       │
        │       ├── Beneficiary.java / Family.java / Individual.java
        │       ├── AidItem.java / FoodPackage.java / MedicalKit.java / EmergencyKit.java / WinterBag.java
        │       ├── DistributionEvent.java
        │       │
        │       ├── DashboardView.java
        │       ├── BeneficiariesView.java
        │       ├── AidItemsView.java
        │       ├── DistributionView.java
        │       ├── ReportsView.java
        │       ├── FilesView.java
        │       │
        │       └── DuplicateRegistrationException.java
        │
        └── resources/
            └── application.css              # UI styling
```

---

## 🛠️ Technologies Used

- **Java 21**
- **JavaFX 21** (Controls, FXML)
- **Maven** (build & dependency management)
- **Object Serialization** for binary persistence
- **IntelliJ IDEA**

---

## ▶️ How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/A7mcdd/SmartAidDistribution-GUI.git
   cd SmartAidDistribution-GUI
   ```

2. **Open in IntelliJ IDEA** as a Maven project (dependencies, including
   JavaFX, are resolved automatically via `pom.xml`).

3. **Run** `application.Main` directly from IntelliJ, or from the terminal:
   ```bash
   mvn clean javafx:run
   ```

---

## 🔗 Related Repository

This project is the second phase of the SmartAid system. The original
console-based version (Phase 1) — simpler, text-only, same core logic —
is available here:

**➡️ [SmartAidDistribution (Phase 1)](https://github.com/A7mcdd/SmartAidDistribution)**

| | Phase 1 | Phase 2 (this repo) |
|---|---|---|
| **Interface** | Console (text-based) | JavaFX GUI |
| **Purpose** | Initial working version / core logic | Final, presentable version |
| **Status** | Archived as-is | Actively showcased |