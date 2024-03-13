# README

LawnKing is the brains of an automated lawn mowing agent and system. — thing Roomba for outdoors.

## Design

High level, the system is made up of two actors: (1) the lawn, and (2) the mower. The idea is a lawn can have many lawnmowers on it, but no lawn mower comes with an inherent knowledge of the lawn. These lawnmowers have to figure out the lawn as they mow. The lawn is forbidden from exchanging coordinates with the lawnmowers; therefore lawnmowers have no frame of reference for discussing positions with themselves. From time t = 0, the number of lawnmowers have to figure this out on their own.


### Sequence
```
╭───────────────╮      ╭──────╮    ╭──────────╮╭────────────────────╮  
│ Setup Context │      │ Lawn │    │ Mower(s) ││ Simulation Context │  
╰───────┬───────╯      ╰───┬──╯    ╰─────┬────╯╰──────────┬─────────╯  
        │                  │             │                │            
        │                  │             │                │            
        │ build from input │             │                │            
        ├╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌▶│             │                │            
        │                  │             │                │            
        │                  │ instantiate │                │            
        │                  ├╌╌╌╌╌╌╌╌╌╌╌╌▶│                │            
        │                  │             │                │            
        │      done        │             │                │            
        │≺╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴┤             │                │            
        │                  │             │                │            
        │                  │   begin     │                │            
        ├╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌▶│            
        │                  │             │                │            
        │                  │     attend to messages       │            
        │                  │◀╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌┤            
        │                  │             │                │            
        │                  │  attend to  │                │            
        │                  │  messages   │                │            
        │                  ├╌╌╌╌╌╌╌╌╌╌╌╌▶│                │            
        │                  │             │                │            
        │                  │             │     work       │            
        │                  │             │◀╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌┤            
        │                  │             │                │            
        │                  │             │ work loop      │            
        │                  │             │ until done     │            
        │                  │             ├╌╌╌╌╌╌╌╌╌╌╌╮    │            
        │                  │             │           │    │            
        │                  │             │◀╌╌╌╌╌╌╌╌╌╌╯    │            
        │                  │             │                │            
        │                  │             │                │            
```

## Roadmap

- [x] Design Domain
- [ ] Implement domain for one lawnmower
- [ ] Handle multiple lawnmowers on a lawn
    - [x] Design inter-actor communication
- [ ] (Optional) Control Plane for modifying inputs
- [ ] Visualize work with some UI
