import Vue from 'vue'
import Todos from '@/components/Todos'
import * as flushPromises from "flush-promises";


describe('todos', () => {
  beforeEach(() => {
    fetch.resetMocks()
  })
  it('render h1', () => {
    fetch.mockResponseOnce([]);
    const Constructor = Vue.extend(Todos)
    const vm = new Constructor().$mount()
    expect(vm.$el.querySelector('#todos h1').textContent).toEqual('Todos List')
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
        expect(fetch.mock.calls[0][0]).toEqual('http://localhost:8060/todos')
        expect(vm.todos).toEqual(json)

        vm.$nextTick().then(() => {
          expect(vm.$el.querySelector('.todo').textContent).toEqual('first todo todo')
          done();
        })
      });
    })
  })
})
