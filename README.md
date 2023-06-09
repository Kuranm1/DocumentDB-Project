# Document-Database
This is Final project for my internship in Atypon 2022.

- I built document database from scratch using Java/SpringBoot.
- Queries of the database are based on HTTP Requests.

- The the project Consist of three part ( Client <= Master => Nodes)  and DemoApplication uses the client to make CRUD operations on the atabase.

  - Client: Is responsible for doing CRUD operations on the node, but first it authenticates it self to the master and the master return node information.
  - Master: Is responsible for authenticating Clients and do Load Balancing on the server nodes.
  - Node: Is the server side of the database which writes/reades from the actual documents on storage

For more details the repo contians PDF files:
- Project design requierments.
- Preject report: the report explains all the deatails in the project.
