# Happy Sword Mod

A Minecraft 1.20.1 Fabric mod that adds a chaotic sword with devastating effects!

## Features

### On Hit (Any Player):
- **Blindness & Nausea**: Screen becomes distorted
- **Weakness & Mining Fatigue**: Victim can barely fight back
- **All Sounds Play**: Every sound in the game plays simultaneously
- **Epileptic Flash Effects**: Rapid bright and dark flashing with particles for 5 seconds
- **Screen Shake**: Velocity applied for disorientation

### On Hit (Cows Only):
- **Flying Chaos**: Cows fly around uncontrollably with random velocities
- **Stretching**: Cows scale up and down rapidly
- **Particle Spam**: Soul fire and other particles explode around them
- **Glitch Teleportation**: Cows randomly teleport nearby (malware-like effect)
- **Super Speed**: Applied speed effect for manic movement
- **Spinning & Rotating**: Cows spin wildly

## Installation

1. Place this mod in your `.minecraft/mods` folder (Fabric 1.20.1)
2. Launch Minecraft with Fabric

## Usage

The Happy Sword works like a normal sword but triggers chaos effects on hit. Only the victim experiences the effects - the attacker sees nothing unusual!

## Building

```bash
./gradlew build
```

The mod JAR will be in `build/libs/`

## Texture

Add your sword texture to: `src/main/resources/assets/happysword/textures/item/happy_sword.png`

The texture should be 16x16 pixels (standard Minecraft item texture format)
