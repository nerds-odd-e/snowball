import Vue from 'vue'
import Todos from '@/components/Todos'
import * as flushPromises from "flush-promises";


describe('todos', () => {
  beforeEach(() => {
    fetch.resetMocks()
  })

  let todoComponent = (() => {
    fetch.mockResponseOnce([]);
    const Constructor = Vue.extend(Todos)
    return new Constructor().$mount()
  })

  it('search elem', () => {
    let todos = [
        {
          "id": 1,
          "status":"todo",
          "title":"first todo"
        },
        {
          "id": 1,
          "status":"todo",
          "title":"second todo"
        },
        {
          "id": 1,
          "status":"todo",
          "title":"other"
        },
    ]
    let vm = todoComponent()
    vm.todos = todos
    vm.searchWord = 'todo'
    expect(vm.searchedTodos.length).toEqual(2)
  }),
  it('exist search elem', () => {
    expect(todoComponent().$el.querySelector('#search').getAttribute('name')).toEqual('search')
  })
  it('render h1', () => {
    expect(todoComponent().$el.querySelector('#todos h1').textContent).toEqual('Todos List')
  })
  it('render todo', (done) => {
    expect(Todos.data()['todos']).toEqual([])

    let json = [{
      "id": 1,
      "status":"todo",
      "title":"first todo"
    }]
    fetch.mockResponseOnce(JSON.stringify(json))

    const Constructor = Vue.extend(Todos)
    const vm = new Constructor().$mount()

    vm.$nextTick().then(() => {
      vm.$nextTick().then(() => {
        expect(fetch.mock.calls.length).toEqual(1)
        expect(fetch.mock.calls[0][0]).toEqual('/todos')
        expect(vm.todos).toEqual(json)

        vm.$nextTick().then(() => {
          expect(vm.$el.querySelector('.todo').textContent).toEqual('first todo todo')
          done();
        })
      });
    })
  })
})
