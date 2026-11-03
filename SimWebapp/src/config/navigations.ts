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
        navTitle:'entities.choixHoraireDemandeur.nav.title',
        navChilds:[
          {
            navTitle: 'entities.choixHoraireDemandeur.nav.list',
            navLink: '/choixHoraireDemandeurs',
          },
          {
            navTitle: 'entities.choixHoraireDemandeur.nav.create',
            navLink: '/choixHoraireDemandeurs/create',
          },
        ]
      },
      
      {
        navTitle:'entities.ciblesOffre.nav.title',
        navChilds:[
          {
            navTitle: 'entities.ciblesOffre.nav.list',
            navLink: '/ciblesOffres',
          },
          {
            navTitle: 'entities.ciblesOffre.nav.create',
            navLink: '/ciblesOffres/create',
          },
        ]
      },
      
      {
        navTitle:'entities.histoOffre.nav.title',
        navChilds:[
          {
            navTitle: 'entities.histoOffre.nav.list',
            navLink: '/histoOffres',
          },
          {
            navTitle: 'entities.histoOffre.nav.create',
            navLink: '/histoOffres/create',
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
        navTitle:'entities.offre.nav.title',
        navChilds:[
          {
            navTitle: 'entities.offre.nav.list',
            navLink: '/offres',
          },
          {
            navTitle: 'entities.offre.nav.create',
            navLink: '/offres/create',
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
        navTitle:'entities.statutOffre.nav.title',
        navChilds:[
          {
            navTitle: 'entities.statutOffre.nav.list',
            navLink: '/statutOffres',
          },
          {
            navTitle: 'entities.statutOffre.nav.create',
            navLink: '/statutOffres/create',
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
      
      {
        navTitle:'entities.typeOffre.nav.title',
        navChilds:[
          {
            navTitle: 'entities.typeOffre.nav.list',
            navLink: '/typeOffres',
          },
          {
            navTitle: 'entities.typeOffre.nav.create',
            navLink: '/typeOffres/create',
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
      {
        navTitle:'views.vRendezVou.nav.title',
        navLink: '/vRendezVous',
      },
      
      {
        navTitle:'views.vRendezVousAccepte.nav.title',
        navLink: '/vRendezVousAcceptes',
      },
      
      {
        navTitle:'views.vRendezVousAnnule.nav.title',
        navLink: '/vRendezVousAnnules',
      },
      
      {
        navTitle:'views.vRendezVousEnAttente.nav.title',
        navLink: '/vRendezVousEnAttentes',
      },
      
      {
        navTitle:'views.vRendezVousRefuse.nav.title',
        navLink: '/vRendezVousRefuses',
      },
      
      
    ],
  },
]
export default navigations
