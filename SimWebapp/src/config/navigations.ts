const navigations = [
  // ENTITIES NAVIGATION
  {
    icon: 'fas fa-layer-group',
    sectionName: 'navbar.entities',
    navChilds: [
      {
        navTitle:'entities.activite.nav.title',
        navChilds:[
          {
            navTitle: 'entities.activite.nav.list',
            navLink: '/activites',
          },
          {
            navTitle: 'entities.activite.nav.create',
            navLink: '/activites/create',
          },
        ]
      },
      
      {
        navTitle:'entities.activitesMembre.nav.title',
        navChilds:[
          {
            navTitle: 'entities.activitesMembre.nav.list',
            navLink: '/activitesMembres',
          },
          {
            navTitle: 'entities.activitesMembre.nav.create',
            navLink: '/activitesMembres/create',
          },
        ]
      },
      
      {
        navTitle:'entities.membre.nav.title',
        navChilds:[
          {
            navTitle: 'entities.membre.nav.list',
            navLink: '/membres',
          },
          {
            navTitle: 'entities.membre.nav.create',
            navLink: '/membres/create',
          },
        ]
      },
      
      {
        navTitle:'entities.plageHoraire.nav.title',
        navChilds:[
          {
            navTitle: 'entities.plageHoraire.nav.list',
            navLink: '/plageHoraires',
          },
          {
            navTitle: 'entities.plageHoraire.nav.create',
            navLink: '/plageHoraires/create',
          },
        ]
      },
      
      {
        navTitle:'entities.rendezVou.nav.title',
        navChilds:[
          {
            navTitle: 'entities.rendezVou.nav.list',
            navLink: '/rendezVous',
          },
          {
            navTitle: 'entities.rendezVou.nav.create',
            navLink: '/rendezVous/create',
          },
        ]
      },
      
      {
        navTitle:'entities.secteur.nav.title',
        navChilds:[
          {
            navTitle: 'entities.secteur.nav.list',
            navLink: '/secteurs',
          },
          {
            navTitle: 'entities.secteur.nav.create',
            navLink: '/secteurs/create',
          },
        ]
      },
      
      {
        navTitle:'entities.statut.nav.title',
        navChilds:[
          {
            navTitle: 'entities.statut.nav.list',
            navLink: '/statuts',
          },
          {
            navTitle: 'entities.statut.nav.create',
            navLink: '/statuts/create',
          },
        ]
      },
      
      {
        navTitle:'entities.statutRendezVou.nav.title',
        navChilds:[
          {
            navTitle: 'entities.statutRendezVou.nav.list',
            navLink: '/statutRendezVous',
          },
          {
            navTitle: 'entities.statutRendezVou.nav.create',
            navLink: '/statutRendezVous/create',
          },
        ]
      },
      
      {
        navTitle:'entities.sujet.nav.title',
        navChilds:[
          {
            navTitle: 'entities.sujet.nav.list',
            navLink: '/sujets',
          },
          {
            navTitle: 'entities.sujet.nav.create',
            navLink: '/sujets/create',
          },
        ]
      },
      
      {
        navTitle:'entities.sujetsMembre.nav.title',
        navChilds:[
          {
            navTitle: 'entities.sujetsMembre.nav.list',
            navLink: '/sujetsMembres',
          },
          {
            navTitle: 'entities.sujetsMembre.nav.create',
            navLink: '/sujetsMembres/create',
          },
        ]
      },
      
      
    ],
  },
  // VIEWS NAVIGATION
  {
    icon: 'fas fa-box',
    sectionName: 'navbar.views',
    navChilds: [
      
    ],
  },
]
export default navigations
