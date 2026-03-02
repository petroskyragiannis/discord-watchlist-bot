# 🎬 Discord Watchlist Bot

[![Java 21](https://img.shields.io/badge/Java-21-orange.svg?style=flat-square&logo=java)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
[![Spring Boot 3.5.10](https://img.shields.io/badge/Spring%20Boot-3.5.10-green.svg?style=flat-square&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-latest-blue.svg?style=flat-square&logo=postgresql)](https://www.postgresql.org/)

**Discord Watchlist Bot** designed to help communities manage their movie and series watchlists effortlessly. Powered by the **OMDb API**, it provides rich metadata, posters, and ratings directly within your server.

---

## 🎮 Usage - Slash Commands

Discord Watchlist Bot uses Discord's native **Slash Commands** for a seamless experience:

- `/watchlist add title: <title>` - Search and add an item to the server's watchlist.
- `/watchlist remove title: <title>` - Remove an item (provides a selection menu if multiple matches exist).
- `/watchlist show category: <all|movies|series>` - Display the current watchlist as rich embeds.

---

## ✨ Key Features

- **🔍 Smart Search**: Instantly fetch movie and series details using the OMDb API.
- **📑 Server-Specific Watchlists**: Each Discord server maintains its own independent watchlist.
- **🎞️ Rich Embeds**: Beautifully formatted movie details including posters, genres, and more.
- **⭐ Reliable Ratings**: Intelligent filtering only displays IMDb ratings with a significant vote count (10k+) for data quality.
- **🛡️ Ephemeral Interactions**: Management commands (add/remove) are private to the user, keeping channels clean.
- **🗂️ Categorized View**: Filter your watchlist by **Movies**, **Series**, or see everything at once.
- **📋 Selection Menus**: Interactive UI for resolving duplicate titles during removal.

---

## 🛠️ Tech Stack

- **Framework**: [Spring Boot 3.5.10](https://spring.io/projects/spring-boot)
- **Discord API**: [JDA (Java Discord API) 6.3.0](https://github.com/discord-jda/JDA)
- **Database**: [PostgreSQL](https://www.postgresql.org/) with [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- **Migrations**: [Flyway](https://flywaydb.org/)
- **HTTP Client**: [Spring WebFlux (WebClient)](https://docs.spring.io/spring-framework/reference/web/webflux-webclient.html)
- **Build Tool**: [Maven](https://maven.apache.org/)

---

## 🚀 Getting Started

### Prerequisites

- **Java 21**
- **PostgreSQL** Database
- **OMDb API Key**: Get one for free at [omdbapi.com](https://www.omdbapi.com/apikey.aspx).
- **Discord Bot Token**: Create an application at the [Discord Developer Portal](https://discord.com/developers/applications) and connect it to your Discord Server.

### Installation & Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/petroskyragiannis/discord-watchlist-bot.git
   ```

2. **Configure Environment Variables**:
   Set the following variables in your environment or IDE:
   - `DISCORD_BOT_TOKEN`: Your Discord Bot Token.
   - `OMDB_API_KEY`: Your OMDb API Key.
   - `DB_URL`: PostgreSQL JDBC URL.
   - `DB_USERNAME`: Database username.
   - `DB_PASSWORD`: Database password.


3. **Build and Run**:
   ```bash
   ./mvnw spring-boot:run
   ```

---



*Developed by [Petros Kyragiannis](https://github.com/petroskyragiannis)*
