Result Announcer
================

A simple web application to be used as a tool to announce the results of an exam. Whole scenario is summarized in [this](http://agaoglu.tumblr.com/post/3764038619/examining-the-examination-results-warm-up) post. This project runs on [scalatra](https://github.com/scalatra/scalatra), [scalate](https://github.com/scalate/scalate) and [scala-query](https://github.com/szeiger/scala-query). Blogged development experiences [here](http://agaoglu.tumblr.com/post/7075871849/announcing-results-with-scalatra).

Building
--------

You will need [sbt](https://github.com/harrah/xsbt/) and a [redis](http://redis.io/) installed to build the project. After cloning `cd` into the project and

    sbt
    update
    jetty-run

This will let you access the login screen. From there you can access the sample data defined in file `db`. It will be loaded into default local redis on app initialization.
