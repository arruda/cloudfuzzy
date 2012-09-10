// package controllers;

// import play.*;
// import play.mvc.*;
// import play.mvc.Http.*;
// import play.data.*;

// public static Result javascriptRoutes() {
//     response().setContentType("text/javascript");
//     return ok(
//         Routes.javascriptRouter("myJsRoutes",
//             routes.javascript.Application.getItem(),
//             routes.javascript.Application.newItem(),
//             routes.javascript.Application.updateItem()
//         )
//     );
// }