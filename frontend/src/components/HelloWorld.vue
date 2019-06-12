//Vue.component('AddTodoForm', {
//  data() {
//    return {
//      title: '',
//    }
//  },
//  methods: {
//    addTodo() {
//       params = {"title": this.title}
//       options = {
//            "method": "POST",
//            "body": JSON.stringify(params)
//       }
//       fetch("/addTodo", options)
//    }
//  },
//  template: `
//     <div>
//         <input name="title"  v-model="title" />
//         <input type="submit" id="add_todo" v-on:click="addTodo" />
//     </div>
//  `,
//})
//
//Vue.component('Todos', {
//  data() {
//    return {
//      todos: [],
//    }
//  },
//  mounted() {
//    fetch("/todos")
//     .then(response => response.json())
//     .then(todos => {
//        this.todos = todos
//     })
//  },
//  template: `
//     <div>
//         <li v-for="todo in todos">{{todo.title}} {{todo.status}}</li>
//     </div>
//  `,
//})
//
//var root = Vue.component('Root', {
//    template: `
//        <div>
//          <add-todo-form></add-todo-form>
//          <todos></todos>
//        </div>
//    `
//})
//

<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  mounted() {
    fetch("/todos")
     .then(response => response.json())
     .then(todos => {
        this.todos = todos
     })
  },
  data () {
    return {
      todos: [],
      msg: 'Welcome to Your Vue.js App'
    }
  }
}
</script>

