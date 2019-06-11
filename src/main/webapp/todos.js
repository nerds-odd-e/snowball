Vue.component('Todos', {
  data: () => {
    return {
      todos: [],
    }
  },
  mounted: function () {
    fetch("/todos")
     .then(response => response.json())
     .then(todos => {
        this.todos = todos
     })
  },
  props: ['todo'],
  template: `
     <div>
         <li v-for="todo in todos">{{todo.title}} {{todo.status}}</li>
     </div>
  `,
})

new Vue({
  el: '#app',
  data: {
    title: ''
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


