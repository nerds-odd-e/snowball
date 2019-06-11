new Vue({
  el: '#app',
  data: {
    todos: [],
    title: ''
  },
  mounted: function () {
    fetch("/todos")
     .then(response => response.json())
     .then(todos => {
        this.todos = todos
     })
  },
  methods: {
    addTodo: function() {
       params = {"title": this.title}
       options= {
            "method": "POST",
            "body": JSON.stringify(params)
       }
       fetch("/addTodo", options)
    }
  }
})

Vue.component('Todos', {
  props: ['todo'],
  template: '<li>{{todo.title}} {{todo.status}}</li>',
})

