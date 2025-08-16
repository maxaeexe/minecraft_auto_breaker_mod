# 🔨 Minecraft Auto Breaker Mod

![Minecraft](https://img.shields.io/badge/Minecraft-1.16.5-green?style=for-the-badge&logo=minecraft)
![Forge](https://img.shields.io/badge/Forge-Compatible-orange?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-8+-blue?style=for-the-badge&logo=java)
![License](https://img.shields.io/badge/License-Educational-yellow?style=for-the-badge)

## ⚠️ **IMPORTANT DISCLAIMER**

> **This mod contains automation features that may violate Minecraft's Terms of Service and server rules. Use at your own risk. Account bans may occur on most servers.**

## 📖 About

Auto Breaker Mod is a Minecraft Forge modification that automatically mines mossy cobblestone blocks within a specified area. The mod features SQL Server integration for user authentication and provides a comprehensive overlay system for monitoring.

## ✨ Features

### 🔐 Security & Authentication
- SQL Server-based user authentication system
- Encrypted database connections
- IP address tracking and logging
- Session management with user validation

### 🎮 Gameplay Features
- **F6 hotkey** for mod toggle
- Automatic mossy cobblestone detection and mining
- Smart area scanning within defined coordinates
- Automatic pickaxe selection and switching
- Y-level based mining with automatic descent
- Intelligent pathfinding and movement

### 🎨 User Interface
- Real-time mod status indicator
- User information display
- Current Y-level monitoring
- Target block information overlay
- Professional login screen with validation

## 🛠️ Installation

### Prerequisites
- Minecraft 1.16.5 (or compatible version)
- Minecraft Forge
- Java 8 or higher
- SQL Server (optional)

### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/minecraft-auto-breaker.git
   cd minecraft-auto-breaker
   ```

2. **Configure Database (Optional)**
   
   If using SQL Server authentication:
   ```java
   // Edit ExampleMod.java
   private static final String DB_URL = "jdbc:sqlserver://YOUR_SERVER;databaseName=YOUR_DB;encrypt=true;trustServerCertificate=true;";
   private static final String DB_USER = "YOUR_USERNAME";
   private static final String DB_PASSWORD = "YOUR_PASSWORD";
   ```

   Create the required table:
   ```sql
   CREATE TABLE maden_users (
       id INT IDENTITY(1,1) PRIMARY KEY,
       kadi NVARCHAR(50) NOT NULL,
       sifre NVARCHAR(100) NOT NULL,
       ip NVARCHAR(45),
       date DATETIME
   );
   ```

3. **Build the mod**
   ```bash
   ./gradlew build
   ```

4. **Install**
   - Place the generated `.jar` file in your `.minecraft/mods` folder
   - Launch Minecraft with Forge

## 🚀 Usage

### Getting Started
1. Launch Minecraft and enter a world
2. Press **F6** to open the authentication screen
3. Enter your credentials (if database is configured)
4. Press **F6** again to toggle the mod on/off

### Controls
| Key | Action |
|-----|--------|
| `F6` | Toggle mod on/off / Open login screen |

### Configuration

#### Mining Area
Edit these coordinates in the code to define your mining area:
```java
private static final int x1 = 111, y1 = 47, z1 = 687; // Corner 1
private static final int x2 = 80, y2 = 46, z2 = 656;  // Corner 2
```

#### Supported Pickaxes
The mod automatically detects and uses:
- Wooden Pickaxe
- Stone Pickaxe
- Iron Pickaxe
- Golden Pickaxe
- Diamond Pickaxe
- Netherite Pickaxe

## 📊 How It Works

### Mining Algorithm
1. **Area Scanning**: Searches for mossy cobblestone within defined coordinates
2. **Pathfinding**: Calculates optimal route to nearest target
3. **Movement**: Automatically approaches blocks beyond 4-block range
4. **Mining**: Breaks blocks with smooth camera rotation
5. **Y-Level Management**: Descends when current level is cleared

### Smart Features
- **Distance-based approach**: Moves closer to distant targets
- **Smooth camera rotation**: Natural-looking head movements
- **Inventory management**: Auto-selects appropriate pickaxe
- **Error handling**: Graceful recovery from mining interruptions

## 🔧 Development

### Project Structure
```
src/main/java/com/example/examplemod/
├── ExampleMod.java              # Main mod class
├── DatabaseManager.java         # SQL Server integration
├── LoginScreen.java            # Authentication GUI
├── KeyInputHandler.java        # Input handling
├── BreakerTickHandler.java     # Mining logic
└── OverlayRenderer.java        # UI overlay
```

### Building from Source
```bash
# Clone repository
git clone https://github.com/yourusername/minecraft-auto-breaker.git

# Navigate to project
cd minecraft-auto-breaker

# Build mod
./gradlew build

# Output will be in build/libs/
```

## 📋 Requirements

### Runtime Dependencies
- Minecraft Forge
- SQL Server JDBC Driver (if using authentication)

### Optional Dependencies
- SQL Server database for user authentication

## ⚠️ Legal & Safety Information

### Risks and Warnings
- **Server Bans**: Most multiplayer servers prohibit automation mods
- **EULA Violations**: May violate Minecraft's End User License Agreement
- **Anti-Cheat Detection**: Modern anti-cheat systems can detect automation
- **Account Suspension**: Risk of permanent account suspension

### Recommended Usage

<table>
<tr>
<td align="center">✅</td>
<td><strong>Single-player worlds</strong> for testing</td>
</tr>
<tr>
<td align="center">✅</td>
<td><strong>Private servers</strong> with explicit permission</td>
</tr>
<tr>
<td align="center">✅</td>
<td><strong>Educational purposes</strong> and mod development learning</td>
</tr>
<tr>
<td align="center">❌</td>
<td><strong>Public multiplayer servers</strong></td>
</tr>
<tr>
<td align="center">❌</td>
<td><strong>Competitive gameplay</strong></td>
</tr>
<tr>
<td align="center">❌</td>
<td><strong>Commercial purposes</strong></td>
</tr>
</table>

## 🐛 Troubleshooting

<details>
<summary><strong>📡 Database Connection Failed</strong></summary>
<br>

```bash
✓ Check SQL Server is running
✓ Verify connection credentials  
✓ Ensure firewall allows connections
✓ Confirm database and table exist
```

</details>

<details>
<summary><strong>⚙️ Mod Not Working</strong></summary>
<br>

```bash
✓ Verify Forge version compatibility
✓ Check mod is in correct folder
✓ Review Minecraft logs for errors
✓ Confirm F6 key binding is not conflicted
```

</details>

<details>
<summary><strong>🔍 No Blocks Found</strong></summary>
<br>

```bash
✓ Verify coordinate configuration
✓ Check Y-level range
✓ Ensure mossy cobblestone exists in area
✓ Confirm player is within range
```

</details>

## 📝 Contributing

<div align="center">

**While this project is primarily educational, contributions are welcome!**

</div>

```mermaid
graph LR
    A[Fork Repository] --> B[Create Feature Branch]
    B --> C[Make Changes]
    C --> D[Commit & Push]
    D --> E[Create Pull Request]
```

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/improvement`)
3. **Commit** changes (`git commit -am 'Add improvement'`)
4. **Push** to branch (`git push origin feature/improvement`)
5. **Create** a Pull Request

> [!NOTE]
> Please ensure all contributions maintain the educational nature of this project.

## 📄 License

<div align="center">

**🎓 EDUCATIONAL PURPOSES ONLY 🎓**

</div>

<table align="center">
<tr>
<th>✅ Allowed</th>
<th>❌ Not Allowed</th>
</tr>
<tr>
<td>
• Learning mod development<br>
• Understanding Minecraft Forge<br>
• Studying automation algorithms
</td>
<td>
• Commercial use<br>
• Distribution as cheat software<br>
• Use on public servers
</td>
</tr>
</table>

## 🤝 Support

<div align="center">

**For educational questions about code structure or Minecraft modding:**

[![Open Issues](https://img.shields.io/github/issues/yourusername/minecraft-auto-breaker?style=for-the-badge&logo=github)](https://github.com/yourusername/minecraft-auto-breaker/issues)

</div>

**When opening an issue:**
- Use the `question` label for educational queries
- Provide detailed development environment information
- Include relevant code snippets and error messages

> [!WARNING]
> **Support is NOT provided for bypassing server protections or using this mod for cheating.**

## 🔗 Related Projects

<div align="center">

| Resource | Link | Description |
|----------|------|-------------|
| 📚 | [Minecraft Forge Documentation](https://docs.minecraftforge.net/) | Official Forge development docs |
| 🎓 | [Minecraft Modding Tutorials](https://moddingtutorials.org/) | Step-by-step modding guides |
| 🌐 | [Forge Community Wiki](https://forge.gemwire.uk/wiki/Main_Page) | Community-driven wiki |

</div>

---

<div align="center">

**⚠️ Remember: This mod is a learning tool. Always respect server rules and game terms of service. ⚠️**

![Educational](https://img.shields.io/badge/Purpose-Educational-green?style=flat-square)
![No Support](https://img.shields.io/badge/Cheating%20Support-None-red?style=flat-square)
![Open Source](https://img.shields.io/badge/Source-Open-blue?style=flat-square)

</div>
