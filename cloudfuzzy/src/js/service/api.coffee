app = angular.module 'cloudfuzzy.api', ['ngResource']

app.factory 'User', ['$resource', ($resource) ->
    $resource '/api/users/:username', username: '@username'
]

app.factory 'System', ['$resource', ($resource) ->
    $resource '/api/systems/:id', id: '@id'
]

# And the nested resources
app.factory 'UserSystem', ['$resource', ($resource) ->
    $resource '/api/users/:username/systems/:id'
]
